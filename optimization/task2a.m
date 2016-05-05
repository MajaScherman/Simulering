function [ output_args ] = task2a()

    A = [2 -1;
        -1 1;
        1 4];
    
    b = [4; 1; 12];
    c = [1; 5];
    
    c = -c; % maximize

    lb = zeros(1, 2);

    options = optimoptions('intlinprog', 'Display', 'off');
    intcon = [1, 2];
    [x, fval, exitflag, output] = intlinprog(c', intcon, A, b, [], [], lb, [], options);

    x
    fval = -fval
end