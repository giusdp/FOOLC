## LET

push function0
push 1

## IN

lfp
push -2
add
lw
print
lfp
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
lfp
push -2
add
lw
print
halt

function0:
cfp
lra
push 1
lfp
add
lw
push 1
add
lfp
push 1
add
sw
sra
pop
pop
sfp
lra
js

