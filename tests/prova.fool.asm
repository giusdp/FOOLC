push function0
lfp
push 1
push 5
push 4
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
push 1
lfp
add
lw
push 2
lfp
add
lw
bl label2
push 0
b label3
label2:
push 1
label3:
push 3
lfp
add
lw
and label0
push 0
b label1
label0:
push 1
label1:
srv
sra
pop
pop
pop
pop
sfp
lrv
lra
js

