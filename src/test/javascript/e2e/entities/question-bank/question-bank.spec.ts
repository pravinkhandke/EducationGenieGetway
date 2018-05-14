import { browser, protractor } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { QuestionBankComponentsPage, QuestionBankUpdatePage } from './question-bank.page-object';

describe('QuestionBank e2e test', () => {
    let navBarPage: NavBarPage;
    let questionBankUpdatePage: QuestionBankUpdatePage;
    let questionBankComponentsPage: QuestionBankComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load QuestionBanks', () => {
        navBarPage.goToEntity('question-bank');
        questionBankComponentsPage = new QuestionBankComponentsPage();
        expect(questionBankComponentsPage.getTitle()).toMatch(/educationGenieApp.questionBank.home.title/);
    });

    it('should load create QuestionBank page', () => {
        questionBankComponentsPage.clickOnCreateButton();
        questionBankUpdatePage = new QuestionBankUpdatePage();
        expect(questionBankUpdatePage.getPageTitle()).toMatch(/educationGenieApp.questionBank.home.createOrEditLabel/);
        questionBankUpdatePage.cancel();
    });

    it('should create and save QuestionBanks', () => {
        questionBankComponentsPage.clickOnCreateButton();
        questionBankUpdatePage.setTitleInput('title');
        expect(questionBankUpdatePage.getTitleInput()).toMatch('title');
        questionBankUpdatePage.setDurationInput('5');
        expect(questionBankUpdatePage.getDurationInput()).toMatch('5');
        questionBankUpdatePage.stateSelectLastOption();
        questionBankUpdatePage.setCreateByInput('createBy');
        expect(questionBankUpdatePage.getCreateByInput()).toMatch('createBy');
        questionBankUpdatePage.setCreatedTimeInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
        expect(questionBankUpdatePage.getCreatedTimeInput()).toContain('2001-01-01T02:30');
        questionBankUpdatePage.setUpdatedByInput('updatedBy');
        expect(questionBankUpdatePage.getUpdatedByInput()).toMatch('updatedBy');
        questionBankUpdatePage.setUpdatedTimeInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
        expect(questionBankUpdatePage.getUpdatedTimeInput()).toContain('2001-01-01T02:30');
        questionBankUpdatePage.userInfoSelectLastOption();
        questionBankUpdatePage.scoreSelectLastOption();
        questionBankUpdatePage.subjectSelectLastOption();
        questionBankUpdatePage.gradeSelectLastOption();
        questionBankUpdatePage.chapterSelectLastOption();
        questionBankUpdatePage.topicSelectLastOption();
        questionBankUpdatePage.save();
        expect(questionBankUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
