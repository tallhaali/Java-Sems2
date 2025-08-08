import random
import time
import os

def thinking_effect():
    print("\nThinking", end="")
    for _ in range(3):
        time.sleep(0.5)
        print(".", end="")
    print("\n")

def clear_screen():
    os.system('cls' if os.name == 'nt' else 'clear')

def get_recursive_challenges():
    return [
        {
            "title": "Factorial",
            "description": "Calculate the factorial of a number n.",
            "hint": "Base case: n == 0 -> return 1. Recursive case: n * factorial(n-1)",
            "answer": '''def factorial(n):
    if n == 0:
        return 1
    return n * factorial(n - 1)'''
        },
        {
            "title": "Countdown",
            "description": "Print numbers from n to 1.",
            "hint": "Base case: n == 0 -> return. Recursive case: print(n), then call countdown(n-1)",
            "answer": '''def countdown(n):
    if n == 0:
        return
    print(n)
    countdown(n - 1)'''
        },
        {
            "title": "Sum to N",
            "description": "Sum numbers from 1 to n.",
            "hint": "Base case: n == 1 -> return 1. Recursive case: n + sum_to_n(n-1)",
            "answer": '''def sum_to_n(n):
    if n == 1:
        return 1
    return n + sum_to_n(n - 1)'''
        },
        {
            "title": "Fibonacci",
            "description": "Return the nth Fibonacci number.",
            "hint": "Base case: n == 0 -> 0, n == 1 -> 1. Recursive case: fib(n-1) + fib(n-2)",
            "answer": '''def fib(n):
    if n == 0:
        return 0
    elif n == 1:
        return 1
    return fib(n - 1) + fib(n - 2)'''
        },
        {
            "title": "Reverse String",
            "description": "Reverse a string using recursion.",
            "hint": "Base case: len(s) <= 1 -> return s. Recursive: reverse(s[1:]) + s[0]",
            "answer": '''def reverse(s):
    if len(s) <= 1:
        return s
    return reverse(s[1:]) + s[0]'''
        },
        {
            "title": "Palindrome Check",
            "description": "Check if a string is a palindrome.",
            "hint": "Base case: len(s) <= 1 -> True. Compare ends, then recurse on the middle.",
            "answer": '''def is_palindrome(s):
    if len(s) <= 1:
        return True
    if s[0] != s[-1]:
        return False
    return is_palindrome(s[1:-1])'''
        },
        {
            "title": "Binary Representation",
            "description": "Print binary representation of a number.",
            "hint": "Base case: n == 0 -> return. Recursive: binary(n//2), then print(n%2)",
            "answer": '''def print_binary(n):
    if n == 0:
        return
    print_binary(n // 2)
    print(n % 2, end='')'''
        }
    ]

def present_challenge(challenge):
    clear_screen()
    print(f"\n🎯 Challenge: {challenge['title']}")
    print(f"📝 Task: {challenge['description']}")
    input("\n🧠 Try solving it yourself first! Press Enter to reveal a hint...")
    print(f"\n💡 Hint: {challenge['hint']}")
    input("\nReady for the solution? Press Enter...")
    thinking_effect()
    print("✅ Solution:")
    print("\n" + challenge["answer"] + "\n")
    input("🔁 Try rewriting it from memory! Press Enter to continue...")

def recursive_challenge_trainer():
    clear_screen()
    print("🌟 Welcome to the Recursive Challenge Trainer!")
    print("Get ready for random recursive problems to sharpen your brain!\n")

    challenges = get_recursive_challenges()
    asked = set()

    while True:
        available = [c for c in challenges if c['title'] not in asked]
        if not available:
            print("🎉 You've completed all challenges! Restarting...\n")
            asked.clear()
            available = challenges[:]
        
        challenge = random.choice(available)
        asked.add(challenge['title'])
        present_challenge(challenge)

        again = input("Do you want another challenge? (y/n): ").lower()
        if again != 'y':
            break

    print("\n🎊 Great session! Keep practicing recursion. See you next time!\n")

if __name__ == "__main__":
    recursive_challenge_trainer()
