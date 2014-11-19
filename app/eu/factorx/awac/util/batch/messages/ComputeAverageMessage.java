/*
 *
 * Instant Play Framework
 * EasyChat
 *                       
 *
 * Copyright (c) 2013/2014 RIMSHOT ITS SPRL.
 * Author Gaston Hollands
 *
 */

package eu.factorx.awac.util.batch.messages;

import eu.factorx.awac.controllers.AverageController;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.forms.AwacCalculator;
import eu.factorx.awac.models.knowledge.Period;

import java.util.List;

public class ComputeAverageMessage {

    Account account;
    AwacCalculator awacCalculator;
    List<eu.factorx.awac.controllers.AverageController.ScopeAndPeriod> scopeAndPeriodList;
    Period period;
    int organizationComputed;
    int scopeComputed;

	public ComputeAverageMessage(
            Account account,
            AwacCalculator awacCalculator,
            List<eu.factorx.awac.controllers.AverageController.ScopeAndPeriod> scopeAndPeriodList,
            Period period,
            int organizationComputed,
            int scopeComputed) {

        this.account = account;
        this.awacCalculator = awacCalculator;
        this.scopeAndPeriodList = scopeAndPeriodList;
        this.period = period;
        this.organizationComputed = organizationComputed;
        this.scopeComputed = scopeComputed;
	}

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public AwacCalculator getAwacCalculator() {
        return awacCalculator;
    }

    public void setAwacCalculator(AwacCalculator awacCalculator) {
        this.awacCalculator = awacCalculator;
    }

    public List<AverageController.ScopeAndPeriod> getScopeAndPeriodList() {
        return scopeAndPeriodList;
    }

    public void setScopeAndPeriodList(List<AverageController.ScopeAndPeriod> scopeAndPeriodList) {
        this.scopeAndPeriodList = scopeAndPeriodList;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public int getOrganizationComputed() {
        return organizationComputed;
    }

    public void setOrganizationComputed(int organizationComputed) {
        this.organizationComputed = organizationComputed;
    }

    public int getScopeComputed() {
        return scopeComputed;
    }

    public void setScopeComputed(int scopeComputed) {
        this.scopeComputed = scopeComputed;
    }

    @Override
    public String toString() {
        return "ComputeAverageMessage{" +
                "account=" + account +
                ", awacCalculator=" + awacCalculator +
                ", scopeAndPeriodList=" + scopeAndPeriodList +
                ", period=" + period +
                ", organizationComputed=" + organizationComputed +
                ", scopeComputed=" + scopeComputed +
                '}';
    }
}