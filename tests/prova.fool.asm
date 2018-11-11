## LET

push function0
push 0
push A_class
new
push 0
push B_class
new
push 0
push C_class
new
push 0
push Z_class
new

## IN

lfp
push -4
lfp
add
lw
push -3
lfp
add
lw
push -2
lfp
add
lw
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
push 0
push C_class
new
srv
sra
pop
pop
pop
pop
sfp
lrv
lra
js

A_class:
B_class:
C_class:
Z_class:
