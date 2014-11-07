package eu.factorx.awac.dto.awac.get;

import java.util.ArrayList;
import java.util.List;

import eu.factorx.awac.dto.DTO;

public class ReportDTO extends DTO {

    private String leftPeriod;
    private String rightPeriod;

    private String leftColor;
    private String rightColor;

    private List<ReportLineDTO> reportLines;
    private String              leftScope1Color;
    private String              leftScope2Color;
    private String              leftScope3Color;
    private String              rightScope1Color;
    private String              rightScope2Color;
    private String rightScope3Color;

    public ReportDTO() {
        super();
        reportLines = new ArrayList<>();
    }

    public List<ReportLineDTO> getReportLines() {
        return reportLines;
    }

    public void setReportLines(List<ReportLineDTO> reportLines) {
		this.reportLines = reportLines;
	}

	public String getLeftPeriod() {
		return leftPeriod;
	}

	public void setLeftPeriod(String leftPeriod) {
		this.leftPeriod = leftPeriod;
	}

	public String getRightPeriod() {
		return rightPeriod;
	}

	public void setRightPeriod(String rightPeriod) {
		this.rightPeriod = rightPeriod;
	}

	public String getLeftColor() {
		return leftColor;
	}

	public void setLeftColor(String leftColor) {
		this.leftColor = leftColor;
	}

	public String getRightColor() {
		return rightColor;
	}

	public void setRightColor(String rightColor) {
		this.rightColor = rightColor;
	}

    public void setLeftScope1Color(String leftScope1Color) {
        this.leftScope1Color = leftScope1Color;
    }

    public String getLeftScope1Color() {
        return leftScope1Color;
    }

    public void setLeftScope2Color(String leftScope2Color) {
        this.leftScope2Color = leftScope2Color;
    }

    public String getLeftScope2Color() {
        return leftScope2Color;
    }

    public void setLeftScope3Color(String leftScope3Color) {
        this.leftScope3Color = leftScope3Color;
    }

    public String getLeftScope3Color() {
        return leftScope3Color;
    }

    public void setRightScope1Color(String rightScope1Color) {
        this.rightScope1Color = rightScope1Color;
    }

    public String getRightScope1Color() {
        return rightScope1Color;
    }

    public void setRightScope2Color(String rightScope2Color) {
        this.rightScope2Color = rightScope2Color;
    }

    public String getRightScope2Color() {
        return rightScope2Color;
    }

    public void setRightScope3Color(String rightScope3Color) {
        this.rightScope3Color = rightScope3Color;
    }

    public String getRightScope3Color() {
        return rightScope3Color;
    }
}
