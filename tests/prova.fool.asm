## LET

push function0
push function1

## IN

lfp
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
push 113
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

function1:
cfp
lra
lfp
lfp
lw
push -1
lfp
lw
add
lw
js
srv
sra
pop
sfp
lrv
lra
js

