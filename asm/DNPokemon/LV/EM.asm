.text /*Basic THUMB Headers, should be included in all ASM Routines*/
.align 2
.thumb
.thumb_func
.global daynightlevelswitch
@.org 0x08800000
 
main:
sub r0, r0, r1
ldr r2, statusbyte /*Loads the ram offset 0x03005542 to R2*/
ldrb r2, [r2] /*Loads the byte contained in the offset*/
ldr r1, timetable
ldrb r2, [r1, r2]
add r2, r0, r2
b check
night:
add r2, r0, #0x0
add r2, #0x10
check: ldrb r1, [r2, #0x3] /*Loads the byte stored at the pointer's location plus 3 for checking*/
cmp r1, #0xFF
beq nodata
cmp r1, #0x8 /*Compares R2 with 0x08, the tell tale sign of a rom pointer, which is what should be stored here if you are using this hack*/
bne nextnormal /*If not equal, load as usual - Thanks Shiny Quagsire!*/
ldr r0, [r2, #0x0] /*Loads the word stored at the location pointed to in R2, the pointer to the wild data*/

nextnormal: add r4, r0, r4 /*Adds the number of bytes from the start of the table that the wild Pokémon slot the game has selected is*/
ldrb r0, [r4, #0x1] /*Loads the upper level limit for the Pokémon if they're stored systematically*/
ldrb r1, [r4, #0x0] /*Loads the lower level limit*/
cmp r0, r1 /*Compares the levels*/
bcc here /*If R0 < R1 goto "here"*/
ldrb r7, [r4, #0x0] /*Loads the lower level limit into R5*/
add r6, r0, #0x0
b here2

here: ldrb r7, [r4, #0x1] /*Reverses the lower and upper level limits to*/
ldrb r6, [r4, #0x0] /*avoid bugs if R0 was smaller than R1 above*/

here2: sub r4, r6, r7 /*Subtracts upper and lower level limits and stores results into R4*/
add r4, #0x1 /*Not entirely sure why, but it's from the original code, and is important for level loading, as leaving it out leads to odd bugs*/
lsl r4, r4, #0x18 /*This line and the next line make sure that you are only passing a byte to the next part of the routine, so it doesn't bug out*/
lsr r4, r4, #0x18
ldr r2, return /*Loads the return offset to R2*/
bx r2 /*Executes a Branch and Exchange goto to the offset contained within the register, ensuring it remains in THUMB mode by being 1 greater than the wanted location*/
nodata: sub r2, r2, r0
cmp r2, #0xC
beq night
add r2, r0, #0x0
b check

.align
statusbyte: .word 0x0203C000
return: .word 0x080B4C93
timetable: .word timetable2
timetable2: .byte 0xC /*Amount to add to pointer per timezone*/
.byte 0x0
.byte 0x4
.byte 0x8
.byte 0xC
.byte 0xC

