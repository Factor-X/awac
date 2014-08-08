require 'rubygems'
require 'pg'
require 'uri'
require 'oj'
require './models.rb'


# Output a table of current connections to the DB

regex = /^([^:]+):([^@]+)@([^\/]+)\/(.*)$/


uri    = URI(ENV['SOURCE'])
remote = PG.connect(
    :dbname   => uri.path[1..-1],
    :host     => uri.host,
    :user     => uri.user,
    :password => uri.password,
    :port     => uri.port || 5432
)

uri   = URI(ENV['TARGET'])
local = PG.connect(
    :dbname   => uri.path[1..-1],
    :host     => uri.host,
    :user     => uri.user,
    :password => uri.password,
    :port     => uri.port || 5432
)

def load_table(conn, table_name, klass)
    tmp     = klass.new
    fields  = (tmp.methods - tmp.class.methods).select { |e| e.to_s.end_with? '=' }.collect { |e| e.to_s[4..-2] }
    results = []
    conn.exec("SELECT " + fields.join(', ') + " FROM " + table_name.to_s) do |result|
        result.each do |row|
            obj = klass.new
            for field in fields
                obj.send("old_#{field}=", row.values_at(field)[0])
            end
            results << obj
        end
    end
    return results
end


def find(conn, table_name, col, val)
    results = []
    conn.exec("SELECT * FROM " + table_name.to_s + " WHERE " + col.to_s + " = $1", [val]) do |result|
        result.each do |row|
            results << row
        end
    end
    return results
end

def single(conn, table_name, col, val)
    results = find(conn, table_name, col, val)
    if results.length == 1
        return results.first
    else
        return nil
    end
end

answer_values        = load_table(remote, :answer_value, AnswerValue)
organizations        = load_table(remote, :organization, Organization)
periods              = load_table(remote, :period, Period)
question_answers     = load_table(remote, :question_answer, QuestionAnswer)
questions            = load_table(remote, :question, Question)
question_set_answers = load_table(remote, :questionsetanswer, QuestionSetAnswer)
scopes               = load_table(remote, :scope, Scope)

# Map the organizations
for organization in organizations
    o = single(local, :organization, :name, organization.old_name)
    if o != nil
        organization.ref_id = o["id"].to_i
    end
end

# Map the periods
for period in periods
    o = single(local, :period, :label, organization.old_name)
    if o != nil
        period.ref_id = o["id"].to_i
    end
end








puts Oj::dump organizations, :indent => 2







