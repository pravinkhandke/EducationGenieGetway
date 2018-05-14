import { element, by } from 'protractor';

export class ContentReviewComponentsPage {
    createButton = element(by.css('#jh-create-entity'));
    title = element.all(by.css('jhi-content-review div h2#page-heading span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ContentReviewUpdatePage {
    PageTitle = element(by.css('h2#jhi-content-review-heading'));
    saveButton = element(by.css('#save-entity'));
    cancelButton = element(by.css('#cancel-save'));
    raitingInput = element(by.css('input#field_raiting'));
    reviewInput = element(by.css('input#field_review'));
    createByInput = element(by.css('input#field_createBy'));
    createdTimeInput = element(by.css('input#field_createdTime'));
    updatedByInput = element(by.css('input#field_updatedBy'));
    updatedTimeInput = element(by.css('input#field_updatedTime'));
    contentSelect = element(by.css('select#field_content'));

    getPageTitle() {
        return this.PageTitle.getAttribute('jhiTranslate');
    }

    setRaitingInput(raiting) {
        this.raitingInput.sendKeys(raiting);
    }

    getRaitingInput() {
        return this.raitingInput.getAttribute('value');
    }

    setReviewInput(review) {
        this.reviewInput.sendKeys(review);
    }

    getReviewInput() {
        return this.reviewInput.getAttribute('value');
    }

    setCreateByInput(createBy) {
        this.createByInput.sendKeys(createBy);
    }

    getCreateByInput() {
        return this.createByInput.getAttribute('value');
    }

    setCreatedTimeInput(createdTime) {
        this.createdTimeInput.sendKeys(createdTime);
    }

    getCreatedTimeInput() {
        return this.createdTimeInput.getAttribute('value');
    }

    setUpdatedByInput(updatedBy) {
        this.updatedByInput.sendKeys(updatedBy);
    }

    getUpdatedByInput() {
        return this.updatedByInput.getAttribute('value');
    }

    setUpdatedTimeInput(updatedTime) {
        this.updatedTimeInput.sendKeys(updatedTime);
    }

    getUpdatedTimeInput() {
        return this.updatedTimeInput.getAttribute('value');
    }

    contentSelectLastOption() {
        this.contentSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    contentSelectOption(option) {
        this.contentSelect.sendKeys(option);
    }

    getContentSelect() {
        return this.contentSelect;
    }

    getContentSelectedOption() {
        return this.contentSelect.element(by.css('option:checked')).getText();
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
