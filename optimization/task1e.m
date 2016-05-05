function [ output_args ] = task5( input_args )
%TASK1 Summary of this function goes here
%   Detailed explanation goes here
%   multiplicera A och b med 10^3 f?r verkligheten
    c_orig = [40; 30; 20; 20; 10];

    A = [2 0 0 0 0;
        0 2 2 2 1;
        0.2 1 0 0.5 0;
        1 0 0 0 0;
        0 0 1 0 0;
        1 1 1 0 0;
        0 0 0 1 1];

    b = [36; 216; 18; 16; 2; 34; 28];

    Aeq=[];
    beq=[];
    c_orig = -c_orig;
    lb = [0 0 0 0 0]';

    options = optimoptions('linprog', 'Algorithm', 'dual-simplex', 'Display', 'off');
    [x_orig, fval, exitflag, output, lambda] = linprog(c_orig, A, b, Aeq, beq, lb, [], [], options);
    %fval = -fval
    delta = 10; % step size
    % finding the lower bound
    c = c_orig;
    x_low = x_orig;
%    while (x_low == x_orig)
%        c(1) = c(1) + delta;
%        [x_low, fval, exitflag, output, lambda] = linprog(c, A, b, Aeq, beq, lb, [], [], options);
%        if (x_low ~= x_orig)
%            break;
%        end
%    end
%    -c+[delta; 0; 0; 0; 0]
%    x_orig
%    x_low
    
    %finding the upper bound
    c = c_orig;
    i = 0;
    while (true)
        c(1) = c(1) - delta;
        [x_high, fval, exitflag, output, lambda] = linprog(c, A, b, Aeq, beq, lb, [], [], options);
        if (x_high ~= x_orig)
            break;
        end
        i = i + 1
        c
    end
    -c-[delta; 0; 0; 0; 0]
    x_orig
    x_high
    
end