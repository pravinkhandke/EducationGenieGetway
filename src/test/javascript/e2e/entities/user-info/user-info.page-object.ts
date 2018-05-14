import { element, by } from 'protractor';

export class UserInfoComponentsPage {
    createButton = element(by.css('#jh-create-entity'));
    title = element.all(by.css('jhi-user-info div h2#page-heading span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class UserInfoUpdatePage {
    PageTitle = element(by.css('h2#jhi-user-info-heading'));
    saveButton = element(by.css('#save-entity'));
    cancelButton = element(by.css('#cancel-save'));
    userTypeInput = element(by.css('input#field_userType'));
    userReviewSelect = element(by.css('select#field_userReview'));
    gradeSelect = element(by.css('select#field_grade'));

    getPageTitle() {
        return this.PageTitle.getAttribute('jhiTranslate');
    }

    setUserTypeInput(userType) {
        this.userTypeInput.sendKeys(userType);
    }

    getUserTypeInput() {
        return this.userTypeInput.getAttribute('value');
    }

    userReviewSelectLastOption() {
        this.userReviewSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    userReviewSelectOption(option) {
        this.userReviewSelect.sendKeys(option);
    }

    getUserReviewSelect() {
        return this.userReviewSelect;
    }

    getUserReviewSelectedOption() {
        return this.userReviewSelect.element(by.css('option:checked')).getText();
    }

    gradeSelectLastOption() {
        this.gradeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    gradeSelectOption(option) {
        this.gradeSelect.sendKeys(option);
    }

    getGradeSelect() {
        return this.gradeSelect;
    }

    getGradeSelectedOption() {
        return this.gradeSelect.element(by.css('option:checked')).getText();
    }

    save() {
        this.saveButton.click();
    }

    cancel() {
        this.cancelButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
