.text
.align 2
.thumb
.thumb_func
.global daynightwildswitch
@.org 0x08800060
main:
ldr r0, [r7, #0x4]
push {r0}
ldr r0, enable_swarming
cmp r0, #0x0
beq noswarm
ldr r0, var_4fff
bl var_decrypt
ldrh r0, [r0, #0x0]
cmp r0, #0x1
bge swarm

noswarm:
pop {r0}
push {r2}
daynight: ldr r2, statusbyte
ldrb r2, [r2]
ldr r1, timetable
ldrb r2, [r1, r2]
add r2, r0, r2
b check

night:
add r2, r0, #0x0
add r2, #0x10
check: ldrb r1, [r2, #0x3]
cmp r1, #0xFF
beq nodata
cmp r1, #0x8
bne nextnormal
ldr r0, [r2, #0x0]

nextnormal: add r0, r4, r0
ldrh r0, [r0, #0x2]
back: ldr r1, lastpokemon
strh r0, [r1, #0x0]
add r1, r5, #0x0
pop {r2}
ldr r3, Back
bx r3

swarm: pop {r0}
ldr r0, var_4ffe
bl var_decrypt
sub r2, r0, #0x2
ldr r1, currentmap
ldr r1, [r1]
ldrb r1, [r1, #0x4]
ldrb r3, [r1, #0x6]
lsl r3, #0x8
add r1, r1, r3
ldrh r2, [r2, #0x0]
cmp r1, r2
bne daynightone
ldrh r0, [r0, #0x0]
b back

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
Back: .word 0x08082b51
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
.align 2
enable_swarming: .word 0x0
