push 1
push 0
and label2
push 0
b label3
label2:
push 1
label3:
push 1
beq label0
push 44
b label1
label0:
push 3
label1:
halt

