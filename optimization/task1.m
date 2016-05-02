function [ output_args ] = task1( input_args )
%TASK1 Summary of this function goes here
%   Detailed explanation goes here



c = [4; 3; 2; 2; 1]*10^3;

A = [2 0 0 0 0; 0 2 2 2 1; 0.2 1 0 0.5 0; 1 0 0 0 0; 0 0 1 0 0; 1 1 1 0 0; 0 0 0 1 1];

b = [36; 216; 18; 16; 2; 34; 28]*10^3;


Aeq=[];
beq=[];
c = -c;
lb=[0 0 0 0 0]';

options = optimoptions('linprog', 'Algorithm', 'simplex', 'Display', 'iter');
[x, fval, exitflag, output, lambda] = linprog(c, A, b, Aeq, beq, lb, [], [], options);
x
fval = -fval
end

