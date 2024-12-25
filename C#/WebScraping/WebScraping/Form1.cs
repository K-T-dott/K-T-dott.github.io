using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Xml.Linq;
using HtmlAgilityPack;

namespace WebScraping
{
    public partial class Form1 : Form
    {

        //画面を起動する
        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        //ボタンクリック時の動作を入れる
        private void button1_Click(object sender, EventArgs e)
        {
            var URL = URLBox.Text;
            string cd = System.Environment.CurrentDirectory;
            string filepath = cd + "/output.csv";

            try
            {
                //Webサイトをとってくる
                var web = new HtmlWeb();
                var document = web.Load(URL);

                //指定された条件に合うテキストをすべて取ってくる
                var nodes = document.DocumentNode.SelectNodes(TagBox.Text);

                //csvファイル書き込み準備
                StreamWriter sw = new StreamWriter(filepath, true);

                // 一つずつデータを出力
                foreach (var node in nodes)
                {
                    sw.WriteLine(HtmlEntity.DeEntitize(node.InnerText) + ",");
                }
                sw.Close();
            }
            catch (Exception ex)
            {
                //URLが入力なしの場合
                if (URLBox.Text.Equals(""))
                {
                    ErrorText.Text = "URLを入力してください";
                }
                //XPathが入力なしの場合
                else if (TagBox.Text.Equals(""))
                {
                    ErrorText.Text = "XPathを入力してください";
                }
                else
                {
                    ErrorText.Text = "不正な入力です\n" + ex.Message;
                    Console.WriteLine("エラー発生" + ex.Message);
                }
            }
        }
    }
}
