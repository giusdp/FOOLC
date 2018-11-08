## LET

push 0
push D_class
new

## IN

lfp
push -1
lfp
add
lw
cts
lw
push 3
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
push 118
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
D_class:
function0
function1
function2
