## LET

push function0
push 3
push 55

## IN

push -2
lfp
add
lw
push -3
lfp
add
lw
lfp
lfp
push -1
lfp
add
lw
js
lfp
lfp
push -1
lfp
add
lw
js
lfp
push -3
add
sw
push -3
lfp
add
lw
halt

## Functions code and Dispatch Table

function0:
cfp
lra
push 7
srv
sra
pop
sfp
lrv
lra
js

A_class:
B_class:
