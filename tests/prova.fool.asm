## LET

push 1
push 3
push 1
push B_class
new
push -1
lfp
add
lw
push 1
beq label4
push 2
push 1
push C_class
new
b label5
label4:
push 1
push 1
push B_class
new
label5:

## IN

lfp
push -2
lfp
add
lw
push 2
push -3
lfp
add
lw
cts
lw
push 2
add
lm
js
print
halt

## Functions code and Dispatch Table

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
push 1
lfp
add
lw
push 0
beq label2
push 0
b label3
label2:
push 1
label3:
push 1
beq label0
lfp
push 2
lfp
add
lw
push 1
lfp
add
lw
push 1
sub
push 0
lfp
add
lw
cts
lw
push 2
add
lm
js
b label1
label0:
lfp
push 2
lfp
add
lw
cts
lw
push 1
add
lm
js
lfp
push 0
lfp
add
lw
cts
lw
push 1
add
lm
js
add
label1:
srv
sra
pop
pop
pop
sfp
lrv
lra
js

function2:
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

A_class:
function0
function1
B_class:
function0
function1
function2
C_class:
function0
function1
A_class:
function0
B_class:
function0
