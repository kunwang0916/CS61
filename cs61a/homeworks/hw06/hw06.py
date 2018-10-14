# Object Oriented Programming

class Fib():
    """A Fibonacci number.

    >>> start = Fib()
    >>> start
    0
    >>> start.next()
    1
    >>> start.next().next()
    1
    >>> start.next().next().next()
    2
    >>> start.next().next().next().next()
    3
    >>> start.next().next().next().next().next()
    5
    >>> start.next().next().next().next().next().next()
    8
    >>> start.next().next().next().next().next().next() # Ensure start isn't changed
    8
    """

    def __init__(self, value=0):
        self.value = value

    def next(self):
        "*** YOUR CODE HERE ***"
        if self.value == 0:
            f = Fib(1)
        else:
            f = Fib(self.value + self.previousValue)
        f.previousValue = self.value
        return f

    def __repr__(self):
        return str(self.value)

class VendingMachine:
    """A vending machine that vends some product for some price.

    >>> v = VendingMachine('candy', 10)
    >>> v.vend()
    'Machine is out of stock.'
    >>> v.deposit(15)
    'Machine is out of stock. Here is your $15.'
    >>> v.restock(2)
    'Current candy stock: 2'
    >>> v.vend()
    'You must deposit $10 more.'
    >>> v.deposit(7)
    'Current balance: $7'
    >>> v.vend()
    'You must deposit $3 more.'
    >>> v.deposit(5)
    'Current balance: $12'
    >>> v.vend()
    'Here is your candy and $2 change.'
    >>> v.deposit(10)
    'Current balance: $10'
    >>> v.vend()
    'Here is your candy.'
    >>> v.deposit(15)
    'Machine is out of stock. Here is your $15.'

    >>> w = VendingMachine('soda', 2)
    >>> w.restock(3)
    'Current soda stock: 3'
    >>> w.restock(3)
    'Current soda stock: 6'
    >>> w.deposit(2)
    'Current balance: $2'
    >>> w.vend()
    'Here is your soda.'
    """
    "*** YOUR CODE HERE ***"
    def __init__(self, name='', price=0):
        self.name = name
        self.price = price
        self.stock_num = 0
        self.balance = 0

    def vend(self):
        if self.stock_num == 0:
            return 'Machine is out of stock.'
        if self.balance < self.price:
            return 'You must deposit ${} more.'.format(self.price - self.balance)
        self.stock_num -= 1
        if self.balance == self.price:
            return 'Here is your {}.'.format(self.name)
        return_amount = self.balance - self.price
        self.balance = 0
        return 'Here is your {} and ${} change.'.format(self.name, return_amount)

    def deposit(self, amount):
        if self.stock_num == 0:
            return 'Machine is out of stock. Here is your ${}.'.format(amount)
        self.balance += amount
        return 'Current balance: ${}'.format(self.balance)

    def restock(self, num):
        self.stock_num += num
        return 'Current {} stock: {}'.format(self.name, self.stock_num)


