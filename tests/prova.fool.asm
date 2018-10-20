push 50
push 3
bleq label2
push 0
b label3
label2:
push 1
label3:
push 1
beq label0
push -1
b label1
label0:
push 3
push 50
mult
label1:
halt

