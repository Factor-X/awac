package eu.factorx.awac.models.code.type;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

@Embeddable
@AttributeOverrides({ @AttributeOverride(name = "key", column = @Column(name = "unit")) })
public class UnitCode extends Code {

	private static final long serialVersionUID = 1L;
	/**
	 * ceci est u litre
	 */
	public static final UnitCode U5101 = new UnitCode("U5101");
    /*
	public static final UnitCode U5102 = new UnitCode("U5102");
	public static final UnitCode U5103 = new UnitCode("U5103");
	public static final UnitCode U5104 = new UnitCode("U5104");
	public static final UnitCode U5105 = new UnitCode("U5105");
	*/
	public static final UnitCode U5106 = new UnitCode("U5106");
    /*
	public static final UnitCode U5108 = new UnitCode("U5108");
	public static final UnitCode U5109 = new UnitCode("U5109");
	public static final UnitCode U5111 = new UnitCode("U5111");
	public static final UnitCode U5112 = new UnitCode("U5112");
	public static final UnitCode U5113 = new UnitCode("U5113");
	public static final UnitCode U5114 = new UnitCode("U5114");
	*/
	public static final UnitCode U5115 = new UnitCode("U5115");
	public static final UnitCode U5116 = new UnitCode("U5116");
	public static final UnitCode U5117 = new UnitCode("U5117");
	public static final UnitCode U5118 = new UnitCode("U5118");
    /*
	public static final UnitCode U5119 = new UnitCode("U5119");
	public static final UnitCode U5120 = new UnitCode("U5120");
	public static final UnitCode U5121 = new UnitCode("U5121");
	*/
	public static final UnitCode U5122 = new UnitCode("U5122");
    /*
	public static final UnitCode U5123 = new UnitCode("U5123");
	public static final UnitCode U5124 = new UnitCode("U5124");
	public static final UnitCode U5125 = new UnitCode("U5125");
	*/
	public static final UnitCode U5126 = new UnitCode("U5126");
    /*
	public static final UnitCode U5127 = new UnitCode("U5127");
	public static final UnitCode U5128 = new UnitCode("U5128");
	public static final UnitCode U5129 = new UnitCode("U5129");
	public static final UnitCode U5130 = new UnitCode("U5130");
	public static final UnitCode U5131 = new UnitCode("U5131");
	public static final UnitCode U5132 = new UnitCode("U5132");
	*/
	public static final UnitCode U5133 = new UnitCode("U5133");
	//public static final UnitCode U5134 = new UnitCode("U5134");
	public static final UnitCode U5135 = new UnitCode("U5135");
    /*
	public static final UnitCode U5136 = new UnitCode("U5136");
	public static final UnitCode U5137 = new UnitCode("U5137");
	public static final UnitCode U5138 = new UnitCode("U5138");
	*/
	public static final UnitCode U5140 = new UnitCode("U5140");
	public static final UnitCode U5141 = new UnitCode("U5141");
	public static final UnitCode U5142 = new UnitCode("U5142");
    /*
	public static final UnitCode U5143 = new UnitCode("U5143");
	public static final UnitCode U5144 = new UnitCode("U5144");
	*/
	public static final UnitCode U5145 = new UnitCode("U5145");
	public static final UnitCode U5146 = new UnitCode("U5146");
	public static final UnitCode U5147 = new UnitCode("U5147");
	public static final UnitCode U5148 = new UnitCode("U5148");

	//public static final UnitCode U5149 = new UnitCode("U5149");
	public static final UnitCode U5150 = new UnitCode("U5150");
	public static final UnitCode U5151 = new UnitCode("U5151");
	//public static final UnitCode U5152 = new UnitCode("U5152");
	public static final UnitCode U5153 = new UnitCode("U5153");
	//public static final UnitCode U5154 = new UnitCode("U5154");
	public static final UnitCode U5155 = new UnitCode("U5155");
	public static final UnitCode U5156 = new UnitCode("U5156");
	//public static final UnitCode U5157 = new UnitCode("U5157");
	//public static final UnitCode U5158 = new UnitCode("U5158");
	public static final UnitCode U5159 = new UnitCode("U5159");
	public static final UnitCode U5160 = new UnitCode("U5160");
	public static final UnitCode U5161 = new UnitCode("U5161");
	public static final UnitCode U5162 = new UnitCode("U5162");
	public static final UnitCode U5163 = new UnitCode("U5163");
	public static final UnitCode U5169 = new UnitCode("U5169");
	public static final UnitCode U5170 = new UnitCode("U5170");
    /*
	public static final UnitCode U5171 = new UnitCode("U5171");
	public static final UnitCode U5172 = new UnitCode("U5172");
	public static final UnitCode U5173 = new UnitCode("U5173");
	public static final UnitCode U5174 = new UnitCode("U5174");
	public static final UnitCode U5175 = new UnitCode("U5175");
	public static final UnitCode U5176 = new UnitCode("U5176");
	public static final UnitCode U5177 = new UnitCode("U5177");
	public static final UnitCode U5178 = new UnitCode("U5178");
	public static final UnitCode U5179 = new UnitCode("U5179");
	public static final UnitCode U5180 = new UnitCode("U5180");
	public static final UnitCode U5181 = new UnitCode("U5181");
	public static final UnitCode U5182 = new UnitCode("U5182");
	public static final UnitCode U5183 = new UnitCode("U5183");
	public static final UnitCode U5184 = new UnitCode("U5184");
	public static final UnitCode U5185 = new UnitCode("U5185");
	public static final UnitCode U5186 = new UnitCode("U5186");
	public static final UnitCode U5187 = new UnitCode("U5187");
	public static final UnitCode U5188 = new UnitCode("U5188");
	public static final UnitCode U5189 = new UnitCode("U5189");
	public static final UnitCode U5190 = new UnitCode("U5190");
	public static final UnitCode U5191 = new UnitCode("U5191");
	public static final UnitCode U5192 = new UnitCode("U5192");
	public static final UnitCode U5193 = new UnitCode("U5193");
	public static final UnitCode U5194 = new UnitCode("U5194");
	public static final UnitCode U5195 = new UnitCode("U5195");
	public static final UnitCode U5196 = new UnitCode("U5196");
	public static final UnitCode U5197 = new UnitCode("U5197");
	public static final UnitCode U5198 = new UnitCode("U5198");
	public static final UnitCode U5199 = new UnitCode("U5199");
	public static final UnitCode U5200 = new UnitCode("U5200");
	public static final UnitCode U5201 = new UnitCode("U5201");
	public static final UnitCode U5202 = new UnitCode("U5202");
	public static final UnitCode U5203 = new UnitCode("U5203");
	public static final UnitCode U5204 = new UnitCode("U5204");
	public static final UnitCode U5205 = new UnitCode("U5205");
	public static final UnitCode U5206 = new UnitCode("U5206");
	public static final UnitCode U5207 = new UnitCode("U5207");
	public static final UnitCode U5208 = new UnitCode("U5208");
	public static final UnitCode U5209 = new UnitCode("U5209");
	public static final UnitCode U5210 = new UnitCode("U5210");
	public static final UnitCode U5211 = new UnitCode("U5211");
	public static final UnitCode U5212 = new UnitCode("U5212");
	public static final UnitCode U5213 = new UnitCode("U5213");
	public static final UnitCode U5214 = new UnitCode("U5214");
	public static final UnitCode U5215 = new UnitCode("U5215");
	public static final UnitCode U5216 = new UnitCode("U5216");
	public static final UnitCode U5217 = new UnitCode("U5217");
	public static final UnitCode U5218 = new UnitCode("U5218");
	public static final UnitCode U5219 = new UnitCode("U5219");
	public static final UnitCode U5220 = new UnitCode("U5220");
	public static final UnitCode U5221 = new UnitCode("U5221");
	public static final UnitCode U5222 = new UnitCode("U5222");
	public static final UnitCode U5223 = new UnitCode("U5223");
	public static final UnitCode U5224 = new UnitCode("U5224");
	public static final UnitCode U5225 = new UnitCode("U5225");
	*/
	public static final UnitCode U5226 = new UnitCode("U5226");
	/*
    public static final UnitCode U5227 = new UnitCode("U5227");
	public static final UnitCode U5228 = new UnitCode("U5228");
	public static final UnitCode U5229 = new UnitCode("U5229");
	public static final UnitCode U5230 = new UnitCode("U5230");
	public static final UnitCode U5231 = new UnitCode("U5231");
	public static final UnitCode U5232 = new UnitCode("U5232");
	public static final UnitCode U5233 = new UnitCode("U5233");
	public static final UnitCode U5234 = new UnitCode("U5234");
	public static final UnitCode U5235 = new UnitCode("U5235");
	public static final UnitCode U5236 = new UnitCode("U5236");
	public static final UnitCode U5237 = new UnitCode("U5237");
	public static final UnitCode U5238 = new UnitCode("U5238");
	public static final UnitCode U5239 = new UnitCode("U5239");
	public static final UnitCode U5240 = new UnitCode("U5240");
	public static final UnitCode U5241 = new UnitCode("U5241");
	public static final UnitCode U5242 = new UnitCode("U5242");
	public static final UnitCode U5243 = new UnitCode("U5243");
	public static final UnitCode U5244 = new UnitCode("U5244");
	public static final UnitCode U5245 = new UnitCode("U5245");
	public static final UnitCode U5246 = new UnitCode("U5246");
	public static final UnitCode U5247 = new UnitCode("U5247");
	public static final UnitCode U5248 = new UnitCode("U5248");
	public static final UnitCode U5249 = new UnitCode("U5249");
	public static final UnitCode U5250 = new UnitCode("U5250");
	public static final UnitCode U5251 = new UnitCode("U5251");
	public static final UnitCode U5252 = new UnitCode("U5252");
	public static final UnitCode U5253 = new UnitCode("U5253");
	public static final UnitCode U5254 = new UnitCode("U5254");
	public static final UnitCode U5255 = new UnitCode("U5255");
	public static final UnitCode U5256 = new UnitCode("U5256");
	public static final UnitCode U5257 = new UnitCode("U5257");
	public static final UnitCode U5258 = new UnitCode("U5258");
	public static final UnitCode U5259 = new UnitCode("U5259");
	public static final UnitCode U5260 = new UnitCode("U5260");
	public static final UnitCode U5261 = new UnitCode("U5261");
	public static final UnitCode U5262 = new UnitCode("U5262");
	public static final UnitCode U5263 = new UnitCode("U5263");
	public static final UnitCode U5264 = new UnitCode("U5264");
	public static final UnitCode U5265 = new UnitCode("U5265");
	public static final UnitCode U5266 = new UnitCode("U5266");
	public static final UnitCode U5267 = new UnitCode("U5267");
	public static final UnitCode U5268 = new UnitCode("U5268");
	public static final UnitCode U5269 = new UnitCode("U5269");
	public static final UnitCode U5270 = new UnitCode("U5270");
	public static final UnitCode U5271 = new UnitCode("U5271");
	public static final UnitCode U5272 = new UnitCode("U5272");
	public static final UnitCode U5273 = new UnitCode("U5273");
	public static final UnitCode U5274 = new UnitCode("U5274");
	public static final UnitCode U5275 = new UnitCode("U5275");
	public static final UnitCode U5276 = new UnitCode("U5276");
	public static final UnitCode U5277 = new UnitCode("U5277");
	public static final UnitCode U5278 = new UnitCode("U5278");
	public static final UnitCode U5279 = new UnitCode("U5279");
	public static final UnitCode U5280 = new UnitCode("U5280");
	public static final UnitCode U5281 = new UnitCode("U5281");
	public static final UnitCode U5282 = new UnitCode("U5282");
	public static final UnitCode U5283 = new UnitCode("U5283");
	public static final UnitCode U5284 = new UnitCode("U5284");
	public static final UnitCode U5285 = new UnitCode("U5285");
	public static final UnitCode U5286 = new UnitCode("U5286");
	public static final UnitCode U5287 = new UnitCode("U5287");
	public static final UnitCode U5288 = new UnitCode("U5288");
	public static final UnitCode U5289 = new UnitCode("U5289");
	public static final UnitCode U5290 = new UnitCode("U5290");
	public static final UnitCode U5291 = new UnitCode("U5291");
	public static final UnitCode U5292 = new UnitCode("U5292");
	public static final UnitCode U5293 = new UnitCode("U5293");
	public static final UnitCode U5294 = new UnitCode("U5294");
	public static final UnitCode U5295 = new UnitCode("U5295");
	public static final UnitCode U5296 = new UnitCode("U5296");
	public static final UnitCode U5297 = new UnitCode("U5297");
	public static final UnitCode U5298 = new UnitCode("U5298");
	public static final UnitCode U5299 = new UnitCode("U5299");
	public static final UnitCode U5300 = new UnitCode("U5300");
	public static final UnitCode U5301 = new UnitCode("U5301");
	public static final UnitCode U5302 = new UnitCode("U5302");
	public static final UnitCode U5303 = new UnitCode("U5303");
	public static final UnitCode U5304 = new UnitCode("U5304");
	public static final UnitCode U5305 = new UnitCode("U5305");
	public static final UnitCode U5306 = new UnitCode("U5306");
	public static final UnitCode U5307 = new UnitCode("U5307");
	public static final UnitCode U5308 = new UnitCode("U5308");
	public static final UnitCode U5309 = new UnitCode("U5309");
	public static final UnitCode U5310 = new UnitCode("U5310");
	public static final UnitCode U5311 = new UnitCode("U5311");
	public static final UnitCode U5312 = new UnitCode("U5312");
	public static final UnitCode U5313 = new UnitCode("U5313");
	public static final UnitCode U5314 = new UnitCode("U5314");
	public static final UnitCode U5315 = new UnitCode("U5315");
	public static final UnitCode U5316 = new UnitCode("U5316");
	public static final UnitCode U5317 = new UnitCode("U5317");
	public static final UnitCode U5318 = new UnitCode("U5318");
	public static final UnitCode U5319 = new UnitCode("U5319");
	public static final UnitCode U5320 = new UnitCode("U5320");
	*/
	public static final UnitCode U5168 = new UnitCode("U5168");
	public static final UnitCode U5321 = new UnitCode("U5321");
	public static final UnitCode U5324 = new UnitCode("U5324");
	public static final UnitCode U5325 = new UnitCode("U5325");

	public static final UnitCode U5327 = new UnitCode("U5327");
	public static final UnitCode U5328 = new UnitCode("U5328");
	public static final UnitCode U5329 = new UnitCode("U5329");
	public static final UnitCode U5330 = new UnitCode("U5330");
	public static final UnitCode U5331 = new UnitCode("U5331");
	public static final UnitCode U5332 = new UnitCode("U5332");
	public static final UnitCode U5335 = new UnitCode("U5335");
	public static final UnitCode U5336 = new UnitCode("U5336");
	public static final UnitCode U5337 = new UnitCode("U5337");

	protected UnitCode() {
		super(CodeList.UNIT);
	}

	public UnitCode(String key) {
		this();
		this.key = key;
	}

}
