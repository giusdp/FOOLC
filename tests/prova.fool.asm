push 0
## DATA
## VTABLE
push A
halt

A:
cfp
lra
mall 1
lfp
push 1
add
lw
lfp
push -2
add
lw
push 0
add
sw
srv
sra
pop
pop
sfp
lrv
lra
js
