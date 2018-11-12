## LET

push function0
push function1
push 12
push 3

## IN

push -1
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
push -6
add
sw
push -6
lfp
add
lw
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

