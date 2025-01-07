#所要時間1時間
#フォルダ内のファイルを1週間以内または1週間後にふるい分けるプログラム
#作成者:Kita Hiroaki
#date:2024/10/02
#実際に業務を行っていた際、「テスト日に撮影した画像はどれだっけ？」となることがあった。
#その際に、画像の分類をするプログラムがあればより探しやすいのではないかと考えた。
#フォルダに入っているファイルは対象外であるため、大事なファイルはフォルダに保存しておけば巻き込まれない。
#画像以外にも、テキストファイルなども変更できるため、ファイルの最新版がどれかわからなくなった際に有用であると考える。

import os
import shutil
from pathlib import Path
import datetime

#必要分のディレクトリを作成するメゾット
def create_directory():
    #日付作成用のfor文
    for i in range(0,8):

        #日付変数dateに日付を代入
        date = datetime.datetime.now() - datetime.timedelta(days = i)
        #dateをもとにディレクトリの名前を作成
        dir_name = os.path.dirname(__file__) + "\\" + date.strftime("%Y_%m_%d")
        
        #「一週間よりも前」のファイル名とそれ以外のディレクトリ名で分ける。
        #存在するディレクトリならば、作成しようとせず文章を表示して終了する
        if i == 7 and not(os.path.exists(dir_name + "以前")):
            os.makedirs(dir_name + "以前")
        elif not(os.path.exists(dir_name)) and not(os.path.exists(dir_name + "以前")):
            os.makedirs(dir_name)
        else:
            print("ディレクトリ"+ dir_name +"は既に存在しています。")

#フォルダ内のファイルを精査し、整理するプログラム
def move_files_to_folder():

    #ディレクトリ内のファイルの名前を精査
    for dir_path, dir_names, file_names in os.walk(os.getcwd()):
    
        #ファイルの名前リストから、ひとつづつファイルを取り出す
        print(file_names)
        for file in file_names:

            #自分を分類ファイルから除外
            if(os.getcwd() + "\\" + file == __file__):
                break
            
            #ファイルの名前をもとにそのファイルの作成時期を表示する。
            dt = datetime.datetime.fromtimestamp(os.path.getctime(file))

            #ファイルの作成時期とフォルダに表示された時期が一致する場合、そのフォルダにファイルを代入
            for i in range(0,8):
                now_time = datetime.datetime.now() - datetime.timedelta(days = i)
                if i == 7:
                    dir_name = now_time.strftime("%Y_%m_%d")
                    shutil.move(file, dir_name+"以前")
                    break
                elif dt.strftime("%Y_%m_%d") == now_time.strftime("%Y_%m_%d"):
                    dir_name = now_time.strftime("%Y_%m_%d")
                    shutil.move(file, dir_name)
                    break

#実行エリア
os.chdir(os.path.dirname(__file__))
create_directory()
move_files_to_folder()