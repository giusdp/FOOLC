## LET

push function0
push 12

## IN

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
halt

function0:
cfp
lra
push 42
lfp
lw
push -2
add
sw
sra
pop
sfp
lrv
lra
js

