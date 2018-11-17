## LET

push function0

## IN

lfp
push -4
push 45
lfp
push -1
lfp
add
lw
js
halt

function0:
cfp
lra
push 1
lfp
add
lw
push 2
lfp
add
lw
add
push -2
lfp
add
lw
push 50
bleq label2
push 0
b label3
label2:
push 1
label3:
push 1
beq label0
push 50
b label1
label0:
push -2
lfp
add
lw
label1:
srv
pop
sra
pop
pop
pop
sfp
lrv
lra
js

