import { Component, OnInit } from '@angular/core';
import {User} from "../../models/User";
import {TokenStorageService} from "../../service/token-storage.service";
import {PostService} from "../../service/post.service";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {NotificationService} from "../../service/notification.service";
import {ImageUploadService} from "../../service/image-upload.service";
import {UserService} from "../../service/user.service";
import {EditUserComponent} from "../edit-user/edit-user.component";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  isUserLoaded = false;
  user: User;
  selectedFile: File;
  userProfileImage: File;
  previewImgUrl: any;

  constructor(
    private tokenService: TokenStorageService,
    private postService: PostService,
    private dialog: MatDialog,
    private notificationService: NotificationService,
    private imageService: ImageUploadService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.userService.getCurrentUser()
      .subscribe(data=>{
        this.user = data;
        this.isUserLoaded = true;
      })

    this.imageService.getProfileImage()
      .subscribe(data=>{
        this.userProfileImage = data;
      })
  }

  onFileSelected(event):void{

    this.selectedFile = event.target.files[0];

    const reader = new FileReader();
    reader.readAsDataURL(this.selectedFile);
    reader.onload = ()=>{this.previewImgUrl = reader.result;}

  }

  openEditDialog(): void{
    const dialogUserConfig = new MatDialogConfig();
    dialogUserConfig.width = '400px';
    dialogUserConfig.data = {
      user: this.user
    }
    this.dialog.open(EditUserComponent, dialogUserConfig);
  }

  onUpload(): void {
    if (this.selectedFile != null) {
      this.imageService.uploadImageToUser(this.selectedFile)
        .subscribe(() => {
          this.notificationService.showSnackBar('Profile Image updated successfully');
        });
    }
  }

  formatImage(img: any): any {
    if (img == null) {
      return null;
    }
  }

}
