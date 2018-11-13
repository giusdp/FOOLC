## LET

push 3
push 55
push 0
push A_class
new
push 0
push B_class
new

## IN

push -2
lfp
add
lw
push -1
lfp
add
lw
push -3
lfp
add
lw
push -4
lfp
add
lw
halt

## Functions code and Dispatch Table

function0:
cfp
lra
push 4
srv
sra
pop
sfp
lrv
lra
js

A_class:
function0
B_class:
