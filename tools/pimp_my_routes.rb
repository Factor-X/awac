lines = IO.readlines('conf/routes')


# first pass: sizes

m_size = 0
p_size = 0
a_size= 0

for l in lines
  l = l.strip
  m = /^([^#\s]+)\s+(\S+)\s+(.*)$/.match(l.strip)
  if m
    method, path, action = m.captures

    if method.length > m_size
      m_size = method.length
    end

    if path.length > p_size
      p_size = path.length
    end

    if action.length > a_size
      a_size = action.length
    end
  end
end

# second pass: padding

File.open('conf/routes', 'w') do |file|
  for l in lines
    l = l.strip
    m = /^([^#\s]+)\s+(\S+)\s+(.*)$/.match(l.strip)
    if m
      method, path, action = /^(\S+)\s+(\S+)\s+(.*)$/.match(l.strip).captures
      file.puts method.ljust(m_size, ' ') + '    ' + path.ljust(p_size, ' ') + '    ' + action.ljust(a_size, ' ')
    else
      file.puts l
    end
  end
end
