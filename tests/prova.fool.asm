## LET

push 0
push B_class
new
push 0
push A_class
new

## IN

lfp
push -1
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
push -2
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
halt

## Functions code and Dispatch Table

function0:
cfp
lra
push 20
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
srv
sra
pop
pop
sfp
lrv
lra
js

function2:
cfp
lra
push 10
srv
sra
pop
sfp
lrv
lra
js

function3:
cfp
lra
lfp
push 1
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
function2
function1
function3
