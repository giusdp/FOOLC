push 0
push 118
push 2
push A_class
new
push function1
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
push 1
lfp
lw
add
lw
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
push 4
srv
sra
pop
sfp
lrv
lra
js

A_class:
push function0
