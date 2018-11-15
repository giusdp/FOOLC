## LET

push function0
push function1
push 4

## IN

lfp
lfp
push -1
lfp
add
lw
js
halt

## Functions code and Dispatch Table

function0:
cfp
lra
push -3
lfp
lw
add
lw
push 4
lfp
lw
push -3
add
sw
lfp
lfp
lw
push -2
lfp
lw
add
lw
js
srv
pop
pop
sra
pop
sfp
lrv
lra
js

function1:
cfp
lra
push 42
srv
sra
pop
sfp
lrv
lra
js

Z_class:
