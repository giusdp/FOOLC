## LET

push function0
push function1

## IN

lfp
lfp
push -1
lfp
add
lw
js
halt

function0:
cfp
lra
lfp
lfp
lw
push -2
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

function1:
cfp
lra
push 3
srv
sra
pop
sfp
lrv
lra
js

