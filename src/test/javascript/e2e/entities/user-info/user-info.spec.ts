import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { UserInfoComponentsPage, UserInfoUpdatePage } from './user-info.page-object';

describe('UserInfo e2e test', () => {
    let navBarPage: NavBarPage;
    let userInfoUpdatePage: UserInfoUpdatePage;
    let userInfoComponentsPage: UserInfoComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load UserInfos', () => {
        navBarPage.goToEntity('user-info');
        userInfoComponentsPage = new UserInfoComponentsPage();
        expect(userInfoComponentsPage.getTitle()).toMatch(/educationGenieApp.userInfo.home.title/);
    });

    it('should load create UserInfo page', () => {
        userInfoComponentsPage.clickOnCreateButton();
        userInfoUpdatePage = new UserInfoUpdatePage();
        expect(userInfoUpdatePage.getPageTitle()).toMatch(/educationGenieApp.userInfo.home.createOrEditLabel/);
        userInfoUpdatePage.cancel();
    });

    it('should create and save UserInfos', () => {
        userInfoComponentsPage.clickOnCreateButton();
        userInfoUpdatePage.setUserTypeInput('userType');
        expect(userInfoUpdatePage.getUserTypeInput()).toMatch('userType');
        userInfoUpdatePage.userReviewSelectLastOption();
        userInfoUpdatePage.gradeSelectLastOption();
        userInfoUpdatePage.save();
        expect(userInfoUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
