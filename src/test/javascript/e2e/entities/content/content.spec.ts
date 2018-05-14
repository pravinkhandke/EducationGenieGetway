import { browser, protractor } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { ContentComponentsPage, ContentUpdatePage } from './content.page-object';

describe('Content e2e test', () => {
    let navBarPage: NavBarPage;
    let contentUpdatePage: ContentUpdatePage;
    let contentComponentsPage: ContentComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Contents', () => {
        navBarPage.goToEntity('content');
        contentComponentsPage = new ContentComponentsPage();
        expect(contentComponentsPage.getTitle()).toMatch(/educationGenieApp.content.home.title/);
    });

    it('should load create Content page', () => {
        contentComponentsPage.clickOnCreateButton();
        contentUpdatePage = new ContentUpdatePage();
        expect(contentUpdatePage.getPageTitle()).toMatch(/educationGenieApp.content.home.createOrEditLabel/);
        contentUpdatePage.cancel();
    });

    it('should create and save Contents', () => {
        contentComponentsPage.clickOnCreateButton();
        contentUpdatePage.setContentTypeInput('contentType');
        expect(contentUpdatePage.getContentTypeInput()).toMatch('contentType');
        contentUpdatePage.setUrlInput('url');
        expect(contentUpdatePage.getUrlInput()).toMatch('url');
        contentUpdatePage.setMediaTypeInput('mediaType');
        expect(contentUpdatePage.getMediaTypeInput()).toMatch('mediaType');
        contentUpdatePage.languageSelectLastOption();
        contentUpdatePage.stateSelectLastOption();
        contentUpdatePage.setCreateByInput('createBy');
        expect(contentUpdatePage.getCreateByInput()).toMatch('createBy');
        contentUpdatePage.setCreatedTimeInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
        expect(contentUpdatePage.getCreatedTimeInput()).toContain('2001-01-01T02:30');
        contentUpdatePage.setUpdatedByInput('updatedBy');
        expect(contentUpdatePage.getUpdatedByInput()).toMatch('updatedBy');
        contentUpdatePage.setUpdatedTimeInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
        expect(contentUpdatePage.getUpdatedTimeInput()).toContain('2001-01-01T02:30');
        contentUpdatePage.subjectSelectLastOption();
        contentUpdatePage.chapterSelectLastOption();
        contentUpdatePage.topicSelectLastOption();
        // contentUpdatePage.gradeSelectLastOption();
        contentUpdatePage.userInfoSelectLastOption();
        contentUpdatePage.save();
        expect(contentUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
