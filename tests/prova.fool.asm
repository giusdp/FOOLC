## LET

push function1
push function2

## IN

lfp
lfp
push -3
lfp
add
lw
js
halt

function0:
cfp
lra
push 113
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
push function0
lfp
lfp
push -2
lfp
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

function2:
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

