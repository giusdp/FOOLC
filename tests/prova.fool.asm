## LET

push 0
push A_class
new
push 0
push D_class
new

## IN

lfp
push -2
lfp
add
lw
cts
lw
push 5
add
lm
js
halt

## Functions code and Dispatch Table

function0:
cfp
lra
push 119
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
sfp
lrv
lra
js

function3:
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
sfp
lrv
lra
js

function4:
cfp
lra
lfp
push 0
lfp
add
lw
cts
lw
push 4
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
B_class:
function0
function1
C_class:
function0
function1
function2
D_class:
function0
function1
function2
function3
function4
