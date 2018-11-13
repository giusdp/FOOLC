## LET

push function0
push 12
push 42

## IN

push 0
push 1
beq label0
push 4
push -3
lfp
add
lw
add
lfp
push -2
add
sw
push -3
lfp
add
lw
push 3
add
lfp
push -3
add
sw
b label1
label0:
push 3
lfp
push -2
add
sw
push 23
lfp
push -3
add
sw
label1:
lfp
lfp
push -1
lfp
add
lw
js
push -2
lfp
add
lw
push -3
lfp
add
lw
halt

function0:
cfp
lra
push 4
push 42
lfp
push -2
add
sw
push -2
lfp
add
lw
srv
pop
sra
pop
sfp
lrv
lra
js

