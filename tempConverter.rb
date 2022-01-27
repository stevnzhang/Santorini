def convert(temp)
    5 * (temp - 32) / 9
end

# TESTS
puts "Converting Fahrenheit to Celsius"
puts convert(32)          
puts convert(50)          
puts convert(212)