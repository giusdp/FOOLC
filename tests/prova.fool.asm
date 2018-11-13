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
nullpush -2
lfp
add
lw
halt

function0:
cfp
lra
push 4
push 42
lfp
push -2
add
sw
push -2
lfp
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

