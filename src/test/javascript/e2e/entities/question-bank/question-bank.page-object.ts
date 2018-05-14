import { element, by } from 'protractor';

export class QuestionBankComponentsPage {
    createButton = element(by.css('#jh-create-entity'));
    title = element.all(by.css('jhi-question-bank div h2#page-heading span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class QuestionBankUpdatePage {
    PageTitle = element(by.css('h2#jhi-question-bank-heading'));
    saveButton = element(by.css('#save-entity'));
    cancelButton = element(by.css('#cancel-save'));
    titleInput = element(by.css('input#field_title'));
    durationInput = element(by.css('input#field_duration'));
    stateSelect = element(by.css('select#field_state'));
    createByInput = element(by.css('input#field_createBy'));
    createdTimeInput = element(by.css('input#field_createdTime'));
    updatedByInput = element(by.css('input#field_updatedBy'));
    updatedTimeInput = element(by.css('input#field_updatedTime'));
    userInfoSelect = element(by.css('select#field_userInfo'));
    scoreSelect = element(by.css('select#field_score'));
    subjectSelect = element(by.css('select#field_subject'));
    gradeSelect = element(by.css('select#field_grade'));
    chapterSelect = element(by.css('select#field_chapter'));
    topicSelect = element(by.css('select#field_topic'));

    getPageTitle() {
        return this.PageTitle.getAttribute('jhiTranslate');
    }

    setTitleInput(title) {
        this.titleInput.sendKeys(title);
    }

    getTitleInput() {
        return this.titleInput.getAttribute('value');
    }

    setDurationInput(duration) {
        this.durationInput.sendKeys(duration);
    }

    getDurationInput() {
        return this.durationInput.getAttribute('value');
    }

    setStateSelect(state) {
        this.stateSelect.sendKeys(state);
    }

    getStateSelect() {
        return this.stateSelect.element(by.css('option:checked')).getText();
    }

    stateSelectLastOption() {
        this.stateSelect
            .all(by.tagName('option'))
            .last()
            .click();
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

    userInfoSelectLastOption() {
        this.userInfoSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    userInfoSelectOption(option) {
        this.userInfoSelect.sendKeys(option);
    }

    getUserInfoSelect() {
        return this.userInfoSelect;
    }

    getUserInfoSelectedOption() {
        return this.userInfoSelect.element(by.css('option:checked')).getText();
    }

    scoreSelectLastOption() {
        this.scoreSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    scoreSelectOption(option) {
        this.scoreSelect.sendKeys(option);
    }

    getScoreSelect() {
        return this.scoreSelect;
    }

    getScoreSelectedOption() {
        return this.scoreSelect.element(by.css('option:checked')).getText();
    }

    subjectSelectLastOption() {
        this.subjectSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    subjectSelectOption(option) {
        this.subjectSelect.sendKeys(option);
    }

    getSubjectSelect() {
        return this.subjectSelect;
    }

    getSubjectSelectedOption() {
        return this.subjectSelect.element(by.css('option:checked')).getText();
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

    chapterSelectLastOption() {
        this.chapterSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    chapterSelectOption(option) {
        this.chapterSelect.sendKeys(option);
    }

    getChapterSelect() {
        return this.chapterSelect;
    }

    getChapterSelectedOption() {
        return this.chapterSelect.element(by.css('option:checked')).getText();
    }

    topicSelectLastOption() {
        this.topicSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    topicSelectOption(option) {
        this.topicSelect.sendKeys(option);
    }

    getTopicSelect() {
        return this.topicSelect;
    }

    getTopicSelectedOption() {
        return this.topicSelect.element(by.css('option:checked')).getText();
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
