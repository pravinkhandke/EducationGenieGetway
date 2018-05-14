import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { ContentListComponentsPage, ContentListUpdatePage } from './content-list.page-object';

describe('ContentList e2e test', () => {
    let navBarPage: NavBarPage;
    let contentListUpdatePage: ContentListUpdatePage;
    let contentListComponentsPage: ContentListComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load ContentLists', () => {
        navBarPage.goToEntity('content-list');
        contentListComponentsPage = new ContentListComponentsPage();
        expect(contentListComponentsPage.getTitle()).toMatch(/educationGenieApp.contentList.home.title/);
    });

    it('should load create ContentList page', () => {
        contentListComponentsPage.clickOnCreateButton();
        contentListUpdatePage = new ContentListUpdatePage();
        expect(contentListUpdatePage.getPageTitle()).toMatch(/educationGenieApp.contentList.home.createOrEditLabel/);
        contentListUpdatePage.cancel();
    });

    it('should create and save ContentLists', () => {
        contentListComponentsPage.clickOnCreateButton();
        contentListUpdatePage.setListTitleInput('listTitle');
        expect(contentListUpdatePage.getListTitleInput()).toMatch('listTitle');
        contentListUpdatePage.contentSelectLastOption();
        contentListUpdatePage.save();
        expect(contentListUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
