import functools
import time
import sys
import inspect
from typing import Callable

# Optional color for better visibility in terminal
try:
    from colorama import Fore, Style, init
    init(autoreset=True)
    COLOR = True
except ImportError:
    COLOR = False

def colored(text, color):
    if COLOR:
        return color + text + Style.RESET_ALL
    return text

# Logging decorator to trace recursion with step-by-step explanation
def trace(func: Callable) -> Callable:
    @functools.wraps(func)
    def wrapper(*args, **kwargs):
        global call_depth
        indent = "|   " * call_depth
        args_repr = ", ".join(repr(a) for a in args)
        print(colored(f"{indent}--> Entering {func.__name__}({args_repr})", Fore.CYAN))

        call_depth += 1
        result = func(*args, **kwargs)
        call_depth -= 1

        print(colored(f"{indent}<-- Returning from {func.__name__}({args_repr}) = {result}", Fore.GREEN))
        return result
    return wrapper

call_depth = 0

# === Recursive functions to trace === #
@trace
def factorial(n):
    print("|   " * call_depth + f"[Logic] If n == 0, return 1 (base case), else return n * factorial(n-1)")
    if n == 0:
        print("|   " * call_depth + f"[Base Case] n == 0 → returning 1")
        return 1
    print("|   " * call_depth + f"[Recursive Step] Computing {n} * factorial({n-1})")
    return n * factorial(n - 1)

@trace
def fibonacci(n):
    print("|   " * call_depth + f"[Logic] If n <= 1, return n. Else return fib(n-1) + fib(n-2)")
    if n <= 1:
        print("|   " * call_depth + f"[Base Case] n == {n} → returning {n}")
        return n
    print("|   " * call_depth + f"[Recursive Step] Computing fib({n-1}) + fib({n-2})")
    return fibonacci(n - 1) + fibonacci(n - 2)

@trace
def sum_to_n(n):
    print("|   " * call_depth + f"[Logic] If n == 1, return 1. Else return n + sum_to_n(n-1)")
    if n == 1:
        print("|   " * call_depth + f"[Base Case] n == 1 → returning 1")
        return 1
    print("|   " * call_depth + f"[Recursive Step] Computing {n} + sum_to_n({n-1})")
    return n + sum_to_n(n - 1)

@trace
def reverse_string(s):
    print("|   " * call_depth + f"[Logic] If len(s) <= 1, return s. Else return reverse(s[1:]) + s[0]")
    if len(s) <= 1:
        print("|   " * call_depth + f"[Base Case] len(s) == {len(s)} → returning '{s}'")
        return s
    print("|   " * call_depth + f"[Recursive Step] Reversing '{s[1:]}' and adding '{s[0]}'")
    return reverse_string(s[1:]) + s[0]

@trace
def is_palindrome(s):
    print("|   " * call_depth + f"[Logic] If len(s) <= 1 → True. Else check s[0] == s[-1] and recurse on middle")
    if len(s) <= 1:
        print("|   " * call_depth + f"[Base Case] len(s) == {len(s)} → returning True")
        return True
    if s[0] != s[-1]:
        print("|   " * call_depth + f"[Mismatch] '{s[0]}' != '{s[-1]}' → returning False")
        return False
    print("|   " * call_depth + f"[Recursive Step] '{s[0]}' == '{s[-1]}' → Checking '{s[1:-1]}'")
    return is_palindrome(s[1:-1])

# === User Interface === #
def run_demo():
    print("\n🔍 Available recursive functions to trace:")
    print("1. factorial(n)")
    print("2. fibonacci(n)")
    print("3. sum_to_n(n)")
    print("4. reverse_string(s)")
    print("5. is_palindrome(s)")
    
    choice = input("\nEnter the number of the function to trace: ").strip()

    if choice == '1':
        n = int(input("Enter n: "))
        print("\n🔎 Tracing factorial:\n")
        factorial(n)
    elif choice == '2':
        n = int(input("Enter n: "))
        print("\n🔎 Tracing fibonacci:\n")
        fibonacci(n)
    elif choice == '3':
        n = int(input("Enter n: "))
        print("\n🔎 Tracing sum_to_n:\n")
        sum_to_n(n)
    elif choice == '4':
        s = input("Enter string: ")
        print("\n🔎 Tracing reverse_string:\n")
        reverse_string(s)
    elif choice == '5':
        s = input("Enter string: ")
        print("\n🔎 Tracing is_palindrome:\n")
        is_palindrome(s)
    else:
        print("❌ Invalid choice")

if __name__ == "__main__":
    run_demo()
