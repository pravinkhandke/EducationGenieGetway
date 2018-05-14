import { element, by } from 'protractor';

export class QuestionsComponentsPage {
    createButton = element(by.css('#jh-create-entity'));
    title = element.all(by.css('jhi-questions div h2#page-heading span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class QuestionsUpdatePage {
    PageTitle = element(by.css('h2#jhi-questions-heading'));
    saveButton = element(by.css('#save-entity'));
    cancelButton = element(by.css('#cancel-save'));
    optionsInput = element(by.css('input#field_options'));
    answersInput = element(by.css('input#field_answers'));
    createByInput = element(by.css('input#field_createBy'));
    createdTimeInput = element(by.css('input#field_createdTime'));
    updatedByInput = element(by.css('input#field_updatedBy'));
    updatedTimeInput = element(by.css('input#field_updatedTime'));
    questionBankSelect = element(by.css('select#field_questionBank'));

    getPageTitle() {
        return this.PageTitle.getAttribute('jhiTranslate');
    }

    setOptionsInput(options) {
        this.optionsInput.sendKeys(options);
    }

    getOptionsInput() {
        return this.optionsInput.getAttribute('value');
    }

    getAnswersInput() {
        return this.answersInput;
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

    questionBankSelectLastOption() {
        this.questionBankSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    questionBankSelectOption(option) {
        this.questionBankSelect.sendKeys(option);
    }

    getQuestionBankSelect() {
        return this.questionBankSelect;
    }

    getQuestionBankSelectedOption() {
        return this.questionBankSelect.element(by.css('option:checked')).getText();
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
