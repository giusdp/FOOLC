## LET

push 12
push 3
push 1
push B_class
new

## IN

push 12
push 12
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
B_class:
function0
