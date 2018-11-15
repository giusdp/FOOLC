## LET

push 12

## IN

push -1
lfp
add
lw
push 6
push 2
mult
mult
print
halt

## Functions code and Dispatch Table

function0:
cfp
lra
push 1
lfp
lw
add
lw
push 55
lfp
lw
push 1
add
sw
push 1
lfp
lw
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

A_class:
function0
