def convert(temp)
    return "Temperature must be an integer" unless temp.class == Integer
    return "Temperature below Absolute Zero" if temp < -474
    5 * (temp - 32) / 9
end

# TESTS
puts "Converting Fahrenheit to Celsius"
puts convert(32)          
puts convert(50)          
puts convert(212)
puts convert("zero")
puts convert(-500)