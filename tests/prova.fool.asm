## LET

push function0
push function1
push function2
push 4

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
lfp
lfp
lw
push -3
lfp
lw
add
lw
js
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
push 42
srv
sra
pop
sfp
lrv
lra
js

function2:
cfp
lra
push 44
srv
sra
pop
sfp
lrv
lra
js

