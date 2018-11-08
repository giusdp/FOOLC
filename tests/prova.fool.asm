## LET

push function0
push 12
push 3
push function1

## IN

push -2
lfp
add
lw
push 6
div
lfp
push -2
add
sw
push -2
lfp
add
lw
lfp
lfp
push -2
lfp
add
lw
js
mult
lfp
push -3
add
sw
halt

function0:
cfp
lra
push 115
srv
sra
pop
sfp
lrv
lra
js

function1:
cfp
lra
push 37
srv
sra
pop
sfp
lrv
lra
js

