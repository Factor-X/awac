alter table unitconversionformula drop constraint fk_j86c83imvkcc5pdcpoff2sus6;

alter table unitconversionformula add CONSTRAINT fk_j86c83imvkcc5pdcpoff2sus6 FOREIGN KEY (unit_id)
      REFERENCES unit (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE cascade;


update answer_value set longdata = 219 where longdata = 217;

update answer_value set longdata = 6 where id = 1302;

-- detect all answer_value used one of old unit
-- select * from answer_value  where longdata = 3 or longdata = 17 or longdata = 4 or longdata = 5 or longdata = 7 or longdata = 8 or longdata = 9 or longdata = 10 or longdata = 11 or longdata = 12 or longdata = 18 or longdata = 19 or longdata = 21 or longdata = 22 or longdata = 23 or longdata = 25 or longdata = 26 or longdata = 27 or longdata = 28 or longdata = 29 or longdata = 30 or longdata = 32 or longdata = 34 or longdata = 35 or longdata = 36 or longdata = 40 or longdata = 41 or longdata = 46 or longdata = 49 or longdata = 51 or longdata = 54 or longdata = 55 or longdata = 63 or longdata = 64 or longdata = 65 or longdata = 66 or longdata = 67 or longdata = 68 or longdata = 69 or longdata = 70 or longdata = 71 or longdata = 72 or longdata = 73 or longdata = 74 or longdata = 75 or longdata = 76 or longdata = 77 or longdata = 78 or longdata = 79 or longdata = 80 or longdata = 81 or longdata = 82 or longdata = 83 or longdata = 84 or longdata = 85 or longdata = 86 or longdata = 87 or longdata = 88 or longdata = 89 or longdata = 90 or longdata = 91 or longdata = 92 or longdata = 93 or longdata = 94 or longdata = 95 or longdata = 96 or longdata = 97 or longdata = 98 or longdata = 99 or longdata = 100 or longdata = 101 or longdata = 102 or longdata = 103 or longdata = 104 or longdata = 105 or longdata = 106 or longdata = 107 or longdata = 108 or longdata = 109 or longdata = 110 or longdata = 111 or longdata = 112 or longdata = 113 or longdata = 114 or longdata = 115 or longdata = 116 or longdata = 117 or longdata = 119 or longdata = 120 or longdata = 121 or longdata = 122 or longdata = 123 or longdata = 124 or longdata = 125 or longdata = 126 or longdata = 127 or longdata = 128 or longdata = 129 or longdata = 130 or longdata = 131 or longdata = 132 or longdata = 133 or longdata = 134 or longdata = 135 or longdata = 136 or longdata = 137 or longdata = 138 or longdata = 139 or longdata = 140 or longdata = 141 or longdata = 142 or longdata = 143 or longdata = 144 or longdata = 145 or longdata = 146 or longdata = 147 or longdata = 148 or longdata = 149 or longdata = 150 or longdata = 151 or longdata = 152 or longdata = 153 or longdata = 154 or longdata = 155 or longdata = 156 or longdata = 157 or longdata = 158 or longdata = 159 or longdata = 160 or longdata = 161 or longdata = 162 or longdata = 163 or longdata = 164 or longdata = 165 or longdata = 166 or longdata = 167 or longdata = 168 or longdata = 169 or longdata = 170 or longdata = 171 or longdata = 172 or longdata = 173 or longdata = 174 or longdata = 175 or longdata = 176 or longdata = 177 or longdata = 178 or longdata = 179 or longdata = 180 or longdata = 181 or longdata = 182 or longdata = 183 or longdata = 184 or longdata = 185 or longdata = 186 or longdata = 187 or longdata = 188 or longdata = 189 or longdata = 190 or longdata = 191 or longdata = 192 or longdata = 193 or longdata = 194 or longdata = 195 or longdata = 196 or longdata = 197 or longdata = 198 or longdata = 199 or longdata = 200 or longdata = 201 or longdata = 202 or longdata = 203 or longdata = 204 or longdata = 205 or longdata = 206 or longdata = 207 or longdata = 208 or longdata = 209 or longdata = 210 or longdata = 211 or longdata = 212 or longdata = 217 or longdata = 224;


-- delete unit
delete from unit where id = 3 or id = 17 or id = 4 or id = 5 or id = 7 or id = 8 or id = 9 or id = 10 or id = 11 or id = 12 or id = 18 or id = 19 or id = 21 or id = 22 or id = 23 or id = 25 or id = 26 or id = 27 or id = 28 or id = 29 or id = 30 or id = 32 or id = 34 or id = 35 or id = 36 or id = 40 or id = 41 or id = 46 or id = 49 or id = 51 or id = 54 or id = 55 or id = 63 or id = 64 or id = 65 or id = 66 or id = 67 or id = 68 or id = 69 or id = 70 or id = 71 or id = 72 or id = 73 or id = 74 or id = 75 or id = 76 or id = 77 or id = 78 or id = 79 or id = 80 or id = 81 or id = 82 or id = 83 or id = 84 or id = 85 or id = 86 or id = 87 or id = 88 or id = 89 or id = 90 or id = 91 or id = 92 or id = 93 or id = 94 or id = 95 or id = 96 or id = 97 or id = 98 or id = 99 or id = 100 or id = 101 or id = 102 or id = 103 or id = 104 or id = 105 or id = 106 or id = 107 or id = 108 or id = 109 or id = 110 or id = 111 or id = 112 or id = 113 or id = 114 or id = 115 or id = 116 or id = 117 or id = 119 or id = 120 or id = 121 or id = 122 or id = 123 or id = 124 or id = 125 or id = 126 or id = 127 or id = 128 or id = 129 or id = 130 or id = 131 or id = 132 or id = 133 or id = 134 or id = 135 or id = 136 or id = 137 or id = 138 or id = 139 or id = 140 or id = 141 or id = 142 or id = 143 or id = 144 or id = 145 or id = 146 or id = 147 or id = 148 or id = 149 or id = 150 or id = 151 or id = 152 or id = 153 or id = 154 or id = 155 or id = 156 or id = 157 or id = 158 or id = 159 or id = 160 or id = 161 or id = 162 or id = 163 or id = 164 or id = 165 or id = 166 or id = 167 or id = 168 or id = 169 or id = 170 or id = 171 or id = 172 or id = 173 or id = 174 or id = 175 or id = 176 or id = 177 or id = 178 or id = 179 or id = 180 or id = 181 or id = 182 or id = 183 or id = 184 or id = 185 or id = 186 or id = 187 or id = 188 or id = 189 or id = 190 or id = 191 or id = 192 or id = 193 or id = 194 or id = 195 or id = 196 or id = 197 or id = 198 or id = 199 or id = 200 or id = 201 or id = 202 or id = 203 or id = 204 or id = 205 or id = 206 or id = 207 or id = 208 or id = 209 or id = 210 or id = 211 or id = 212 or id = 217 or id = 224;


select * from answer_value  where longdata = 2;

delete from unit where id = 2;