## LET

push function0
push function1
push 3

## IN

lfp
push 23
lfp
push -2
lfp
add
lw
js
halt

function0:
cfp
lra
push 1
lfp
add
lw
lfp
lw
push -3
add
sw
sra
pop
pop
sfp
lra
js

function1:
cfp
lra
lfp
push 1
lfp
add
lw
lfp
lw
push -1
lfp
lw
add
lw
js
sra
pop
pop
sfp
lra
js

