import { browser, protractor } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { QuestionsComponentsPage, QuestionsUpdatePage } from './questions.page-object';

describe('Questions e2e test', () => {
    let navBarPage: NavBarPage;
    let questionsUpdatePage: QuestionsUpdatePage;
    let questionsComponentsPage: QuestionsComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Questions', () => {
        navBarPage.goToEntity('questions');
        questionsComponentsPage = new QuestionsComponentsPage();
        expect(questionsComponentsPage.getTitle()).toMatch(/educationGenieApp.questions.home.title/);
    });

    it('should load create Questions page', () => {
        questionsComponentsPage.clickOnCreateButton();
        questionsUpdatePage = new QuestionsUpdatePage();
        expect(questionsUpdatePage.getPageTitle()).toMatch(/educationGenieApp.questions.home.createOrEditLabel/);
        questionsUpdatePage.cancel();
    });

    it('should create and save Questions', () => {
        questionsComponentsPage.clickOnCreateButton();
        questionsUpdatePage.setOptionsInput('options');
        expect(questionsUpdatePage.getOptionsInput()).toMatch('options');
        questionsUpdatePage
            .getAnswersInput()
            .isSelected()
            .then(selected => {
                if (selected) {
                    questionsUpdatePage.getAnswersInput().click();
                    expect(questionsUpdatePage.getAnswersInput().isSelected()).toBeFalsy();
                } else {
                    questionsUpdatePage.getAnswersInput().click();
                    expect(questionsUpdatePage.getAnswersInput().isSelected()).toBeTruthy();
                }
            });
        questionsUpdatePage.setCreateByInput('createBy');
        expect(questionsUpdatePage.getCreateByInput()).toMatch('createBy');
        questionsUpdatePage.setCreatedTimeInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
        expect(questionsUpdatePage.getCreatedTimeInput()).toContain('2001-01-01T02:30');
        questionsUpdatePage.setUpdatedByInput('updatedBy');
        expect(questionsUpdatePage.getUpdatedByInput()).toMatch('updatedBy');
        questionsUpdatePage.setUpdatedTimeInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
        expect(questionsUpdatePage.getUpdatedTimeInput()).toContain('2001-01-01T02:30');
        questionsUpdatePage.questionBankSelectLastOption();
        questionsUpdatePage.save();
        expect(questionsUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
