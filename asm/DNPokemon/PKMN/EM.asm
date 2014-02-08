.text
.align 2
.thumb
.thumb_func
.global daynightwildswitch
@.org 0x08800060
main:
pop {r0}
ldrb r0, [r0, #0x0]
push {r3,r4}
lsl r4, r0, #0x2
ldr r0, [r5, #0x4]

daynight: ldr r1, statusbyte
ldrb r1, [r1]
ldr r3, timetable
ldrb r1, [r3, r1]
add r1, r0, r1
b check

night:
add r1, r0, #0x0
add r2, #0xC
b check

day:
add r1, r0, #0x0

check: ldrb r3, [r1, #0x3]
cmp r3, #0xFF
beq nodata
cmp r3, #0x8
bne nextnormal
ldr r0, [r1, #0x0]

nextnormal: add r0, r0, r4
ldrh r0, [r0, #0x2]
pop {r3,r4}
add r1, r4, #0x0
ldr r6, Back
bx r6

var_decrypt: ldr r1, vardecrypt
bx r1

daynightone: ldr r0, [r7, #0x4]
push {r0}
b daynight

nodata: sub r2, r2, r0
cmp r2, #0xC
beq night
add r2, r0, #0x0
b check


.align
Back: .word 0x080B5015
lastpokemon: .word 0x0300555C
statusbyte: .word 0x0203C000
vardecrypt: .word 0x0806E455
var_4fff: .word 0x00004FFF /*Replace this with whatever variable you would like to use*/
var_4ffe: .word 0x00004FFE /*Replace this with whatever variable you would like to use*/
currentmap: .word 0x03005008 /*Save block for maps. Here it contains a pointer to the actual map header and player position*/
timetable: .word timetable2
timetable2: .byte 0xC /*Amount to add to pointer per timezone*/
.byte 0x0
.byte 0x4
.byte 0x8
.byte 0xC
.byte 0xC
